const nameInput = document.getElementById("namefield");
const emailInput = document.getElementById("emailfield");
const ageInput = document.getElementById("agefield");
const phoneInput = document.getElementById("phonefield");
const save = document.getElementById("saveBtn");
const message = document.getElementById("message");


const profileId = localStorage.getItem("profileId");
async function loadProfile() {
    const response = await apiGet("/patient/profile/" + profileId);
    if(response === null){
        return;
    }
    if(!response.ok){
        message.textContent = "Server error";
        return;
    }

    const data = await response.json();
    nameInput.value = data.user.name;
    emailInput.value = data.user.email;
    ageInput.value = data.age;
    phoneInput.value = data.phone;
}

save.addEventListener("click", async function () {
   const ageValue = Number(ageInput.value);
   const phoneValue = phoneInput.value;

  const body = {
        age : ageValue,
        phone : phoneValue
    };

    const res = await apiPut("/patient/update/" + profileId, body);
    if(res.ok){
        message.textContent = "Profile Updated";
    }else{
        const err = await res.json();
        message.textContent = err.message;
    }
    
})
loadProfile();