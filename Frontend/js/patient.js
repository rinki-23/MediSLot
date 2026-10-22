const doctorList = document.getElementById("doctorList");
const message = document.getElementById("message");

async function loadDoctors() {
    try{

       const response = await apiGet("/doctor/getall");
       if(response === null){
        return;
       }

       // convert the json response into a js array
     const doctors = await response.json();
     if(doctors.length === 0){
        message.textContent = "No doctors found";
        return;
     }

      doctors.forEach(function(doctor){
        // create an empty li
        const item = document.createElement("li");
        //put the text inside it
        item.textContent = doctor.user.name + " - " + doctor.specialization + " - " + doctor.experience + "  years - Rs " + doctor.fees; 

        // create view slots button
        const slotBtn = document.createElement("button");
        slotBtn.textContent = "View Slots";
        slotBtn.addEventListener("click", function(){
            window.location.href = "slots.html?doctorId=" + doctor.id;
            
        })

        item.appendChild(slotBtn);
        // add li to ul so it shows on the page
        doctorList.appendChild(item);
      }) 
    }catch(err){
        console.log(err);
        message.textContent = "Cannot connnect to server";
    }
    
}

loadDoctors();
document.getElementById("logoutBtn").addEventListener("click" , function(){
    localStorage.clear();
    window.location.href = "login.html";
})

document.getElementById("myAppBtn").addEventListener("click", function(){
    window.location.href = "appointment.html";
})