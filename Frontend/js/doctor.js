const  message = document.getElementById("message");
const appointment = document.getElementById("appointmentList");

const doctorId =  localStorage.getItem("profileId");

async function loadAppointments(){
   
    try{
       const response =  await apiGet("/appointments/doctor/" + doctorId);
       if(!response.ok){
        message.textContent = "Something went wrong";
        return;
       }

       const result = await response.json();
       appointment.innerHTML = "";
       message.textContent = "";
       
       if(result.length === 0){
        message.textContent = "No appointments yet";
        return;
       }
      

       result.forEach(function(a){
        const li = document.createElement("li");

        // left part
        const info = document.createElement("span");
        info.textContent = a.patient.user.name + " | " + a.slot.slotdate + " | " + a.slot.startTime.substring(0,5);

        //right part 
        const badge = document.createElement("span");
        badge.textContent =  a.status;
        badge.className = "badge " + a.status.toLowerCase();

        li.appendChild(info);
        li.appendChild(badge);

        // show the complete button only for Booked appointment
        if(a.status === "BOOKED"){
            const completeBtn = document.createElement("button");
            completeBtn.type = "button";
            completeBtn.textContent = "Complete";

            completeBtn.addEventListener("click", async function () {
                
                const diagnosis = prompt("Diagnosis: ");
                if(diagnosis === null || diagnosis.trim() === ""){
                    message.textContent = "Diagnosis is required";
                    return;
                }

                const prescription = prompt("Prescription : ");
                if(prescription === null || prescription.trim() === ""){
                    message.textContent = "Prescription is required";
                    return;
                }

                // send notes to backend
                const res = await apiPut("/appointments/complete/" + a.id, {
                    diagnosis : diagnosis,
                    prescription : prescription,
                });

                if(res === null){
                    return;
                }

                if(res.ok){
                    await loadAppointments();
                    message.textContent = "Appointments completed";
                }else{
                    const err = await res.json();
                    message.textContent = err.message;
                }
            });
            li.appendChild(completeBtn);
        }
        appointment.appendChild(li);
        })

    }catch(err){
        console.log(err);
        message.textContent = "Cannot connect to server";
    }
}
loadAppointments();