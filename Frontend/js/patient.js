// page elements
const doctorList = document.getElementById("doctorList");
const message = document.getElementById("message");
const searchInput = document.getElementById("searchInput");

// this array keeps all doctors that came from backend
// we need it so that we can search again and again without calling the backend
let allDoctors = [];

// get doctors from backend
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

     // save the full list so that search can use it later
     allDoctors = doctors;

     // first show all doctors
      showDoctor(allDoctors);

 }catch(err){
        console.log(err);
        message.textContent = "Cannot connnect to server";
    }
    
}

// show a given list of doctors
// lis can be the full list or filtered list(after search)
function showDoctor(list){
        doctorList.innerHTML = "";
        message.textContent = "";
        if(list.length === 0){
            message.textContent = "No doctor found";
        }

        list.forEach(function(doctor){
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
     }

     searchInput.addEventListener("input", function(){
    const text = searchInput.value.toLowerCase();
    const filtered = allDoctors.filter(function(d){
        return d.user.name.toLowerCase().includes(text) || d.specialization.toLowerCase().includes(text);
    });

    // show only filtered doctor
    showDoctor(filtered);
   
})  


loadDoctors();



document.getElementById("logoutBtn").addEventListener("click" , function(){
    localStorage.clear();
    window.location.href = "login.html";
})

document.getElementById("myAppBtn").addEventListener("click", function(){
    window.location.href = "appointment.html";
})