// search doctorId from url
const param = new URLSearchParams(window.location.search);
const doctorId = param.get("doctorId");
console.log(doctorId);

const date = document.getElementById("dateInput");
const message = document.getElementById("message");
const searchBtn = document.getElementById("searchBtn");
const slotList = document.getElementById("slotList");
const doctorInfo = document.getElementById("doctorInfo");
// add event listener on search btn
searchBtn.addEventListener("click", showSlot)
async function showSlot() {
   const dateValue =  date.value;
   if(!dateValue){
    message.innerText = "Please select a date";
    return;
   }
   slotList.innerHTML = "";
  message.textContent = "";

   const response = await apiGet("/slot/available/" + doctorId + "/" + dateValue);
    if(response === null){
        return;
       }
  const slot = await response.json();
  if(slot.length === 0){
    message.textContent = "No slots available for this date";
    return; 
  }

  

  slot.forEach(function(slots){
    console.log(slots);
    const item = document.createElement("li");
    const button = document.createElement("Button");
    button.textContent = "Book";
    const time = slots.startTime;
    doctorInfo.textContent = slots.doctor.user.name + " - " + slots.doctor.specialization + " - Rs" + slots.doctor.fees;
    item.textContent = dateValue + "    |   " + time.substring(0,5) + " ";


    button.addEventListener("click", async function(){
      const profileId = localStorage.getItem("profileId");
      const bookResponse = await apiPost("/appointments/book/" + profileId + "/" + slots.id);
     
      await showSlot();
      if(bookResponse.ok){
        message.textContent = "Appointment Booked";
      }
    })


    item.appendChild(button);
    slotList.appendChild(item);
  })
}
