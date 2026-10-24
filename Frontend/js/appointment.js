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
        li.textContent = appointment.doctor.user.name + " | " + appointment.doctor.specialization + " | " + appointment.slot.slotdate + " | " + appointment.slot.startTime.substring(0,5) + " | " + appointment.status + "     ";
        
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
      
        appList.appendChild(li);
    });
}
loadAppointment();

backBtn.addEventListener("click", function(){
    window.location.href = "patient.html";
  })