const doctors = document.getElementById("doctorList");
const message = document.getElementById("message");
 
async function loadDoctors() {
    doctors.innerHTML = "";
    message.innerHTML = "";
    const response = await apiGet("/admin/doctors");
    if(response === null){
        return;
    }
    if(!response.ok){
        message.textContent = "Server error";
        return;
    }

    const result = await response.json();
    result.forEach(function(doctor){
         const li = document.createElement("li");
         const text = document.createElement("span");
         const badge = document.createElement("span");
         badge.className = "badge " + (doctor.active ? "Active" : "Disable");
        const button = document.createElement("button");
        text.textContent =  doctor.user.name + " - " + doctor.specialization + " - " + doctor.experience + "  years - Rs " + doctor.fees;
       badge.textContent = doctor.active ? "Active" : "Disable";

        button.textContent = doctor.active ? "Disable" : "Enable";
        li.appendChild(text);
        li.appendChild(badge);
         li.appendChild(button);
        doctors.appendChild(li);
       
         button.addEventListener("click", async function() {
            if(!confirm("Change the doctor status")){
                return;
            }
            const body = {
                active : !doctor.active
            }
            const result = await apiPut("/admin/doctors/" + doctor.id + "/status", body);
            if(result === null){
                return;
            }
            if(result.ok){
                message.textContent = "Successfull";
                await loadDoctors();

            }else{
                const err = await result.json();
                message.textContent = err;
            }
        })
 })
    
}
loadDoctors();