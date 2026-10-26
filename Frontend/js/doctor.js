const  message = document.getElementById("message");
const appointment = document.getElementById("appointmentList");

async function loadAppointments(){
   const doctorId =  localStorage.getItem("profileId");
    try{
       const response =  await apiGet("/appointments/doctor/" + doctorId);
       if(!response.ok){
        message.textContent = "Something went wrong";
        return;
       }

       const result = await response.json();
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
        const status = document.createElement("span");
        status.textContent =  a.status;
        status.className = "badge " + a.status.toLowerCase();

        li.appendChild(info);
        li.appendChild(status);
        appointment.appendChild(li);
        })

    }catch(err){
        console.log(err);
    }
}
loadAppointments();