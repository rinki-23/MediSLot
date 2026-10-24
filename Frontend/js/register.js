const name = document.getElementById("name");
const email = document.getElementById("email");
const password = document.getElementById("password");
const age = document.getElementById("age");
const phone = document.getElementById("phone");
const message = document.getElementById("message");
const registerBtn = document.getElementById("registerBtn");

registerBtn.addEventListener("click", async function (){
    try{

    
    const nameInput = name.value;
    const emailInput = email.value;
    const passwordInput = password.value;
    const ageInput = Number(age.value);
    const phoneInput = phone.value;

    if(nameInput === "" || emailInput ===  "" || passwordInput === "" || 
         phoneInput === null){
            message.textContent = "All fields are required";
            return;
        }

        if(isNaN(ageInput) || ageInput<1 || ageInput>120){
            message.textContent = "Enter a valid age(1-120)";
            return;
        }
        const response = await fetch(BASE_URL + "/users/register", 
            {
                method : "POST",
                headers : {
                    "Content-Type" : "application/json"
                },
                body : JSON.stringify({
                    name : nameInput,
                    email: emailInput, 
                    password : passwordInput,
                    age :ageInput, 
                    phone : phoneInput
                })
            }
        )

        const data = await response.json();
        if(response.ok){
            alert("Registered! Please Login");
            window.location.href = "login.html";
        }else{
            message.textContent = data.message || "Registration failed";
        }
    }catch(err){
        console.log(err);
        message.textContent = "Cannot connect to server";
    }
    
})
