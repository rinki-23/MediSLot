const message = document.getElementById("message");
const appList = document.getElementById("appointmentList");
const backBtn = document.getElementById("backBtn");

async function loadAppointment(){
   const patientId =  localStorage.getItem("profileId");
    appList.textContent = "";
    const response = await apiGet("/appointments/patient/" + patientId);
    if(!response.ok){
        message.textContent = "Could not load appointmets";
        return;
    }


    const result = await response.json();
    if(result.length === 0){
        message.textContent = "No appointments yet";
        return;
    }
    
    result.forEach(function(appointment){
        const li = document.createElement("li");
        const info = document.createElement("span");
        const badge = document.createElement("span");
        
        info.textContent = appointment.doctor.user.name + " | " + appointment.doctor.specialization + " | " + appointment.slot.slotdate + " | " + appointment.slot.startTime.substring(0,5);  
        badge.textContent = appointment.status;
        badge.className = "badge "+ appointment.status.toLowerCase();

        const left = document.createElement("div");
        left.className = "app-main";
        left.appendChild(info);
        left.appendChild(badge);
        li.appendChild(left);


        if(appointment.status === "BOOKED"){
            const cancel = document.createElement("button");
            cancel.textContent = "Cancel";
            cancel.addEventListener("click", async function(){
                // ask before cancelling
                if(!confirm("Cancel this appointment?")){
                    return;
                }
              const cancelResponse =  await apiPut("/appointments/cancel/" + appointment.id);
              if(cancelResponse === null){
                return;
              }
              if(cancelResponse.ok){
                await loadAppointment();
                message.textContent = "Appointment Cancelled";
              }else{
                const data = await cancelResponse.json();
                message.textContent = data.message || "Cancel failed";
              }
            });
              li.appendChild(cancel);
        }

        if(appointment.status === "COMPLETED"){
          const notes = document.createElement("p");
          notes.className = "notes";
          notes.textContent = "Diagnosis: " + appointment.diagnosis + " | " + "Prescription: " + appointment.prescription;
          li.appendChild(notes);
        }
      
        appList.appendChild(li);
    });
}
loadAppointment();

backBtn.addEventListener("click", function(){
    window.location.href = "patient.html";
  })