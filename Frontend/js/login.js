const email = document.getElementById("email");
const password = document.getElementById("password");
const btn = document.getElementById("loginBtn");
const error = document.getElementById("errorMsg");

btn.addEventListener("click", async function(){
    // clear any old message
    error.textContent = "";
    const emailValue = email.value;
    const passwordValue = password.value;

    if(emailValue === "" || passwordValue === ""){
    error.textContent = "Please enter email and password";
    return;    
    }

    try{
        // send request to our backend
        // BASE_URL = http://localhost:8080/
        // URL = http://localhost:8080/users/login
        // await = wait until backend reply
        const response = await fetch(BASE_URL + "/users/login",{
            method: "POST",
            headers: {"Content-Type": "application/json"},
            // JSON.stringify() = convert JS to JSON text
            body: JSON.stringify({email : emailValue, password : passwordValue})
        });

        // convert the response body(JSON text) into a JS object
        const data = await response.json();
        if(response.ok){
            // login success : save the token in the browser for later requests
            localStorage.setItem("token", data.token);
            alert("login successfully");
        }else{
            // login failed : show the message sent by our backend
            error.textContent = data.message || "login failed";
        }
    }catch(err){
        error.textContent = "Cannot connect to server";
    }
})