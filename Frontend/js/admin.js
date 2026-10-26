const name = document.getElementById("name");
const email = document.getElementById("email");
const password = document.getElementById("password");
const specialization = document.getElementById("specialization");
const experience = document.getElementById("experience");
const fees = document.getElementById("fees");
const addBtn = document.getElementById("addBtn");
const message = document.getElementById("message");

addBtn.addEventListener("click", async function () {
    const nameInput = name.value;
    const emailInput = email.value;
    const passwordInput = password.value;
    const specializationInput = specialization.value;
    const experienceInput = Number(experience.value);
    const feesInput = Number(fees.value);

    if(nameInput === "" || emailInput === "" || passwordInput === "" || specializationInput === ""
        || experienceInput === 0 || experienceInput<0 || feesInput === 0 || feesInput<0){
            message.textContent = "Fill all fields correctly";
        }
      
        const body = {
            name : name.value,
            email : email.value,
            password : password.value,
            specialization : specialization.value,
            experience : Number(experience.value),
            fees : Number(fees.value)

        }

        const response = await apiPost("/admin/doctors", body);
        if(response === null){
            return;
        }
        if(response.ok){
            message.textContent = "Doctor Added successfully";
            name.value = "";
        email.value = "";
        password.value = "";
        specialization.value = "";
        experience.value = "";
        fees.value = "";
        

        }else{
            const err = await response.json();
            message.textContent = "Server error", err.message;
            

        }

       
})