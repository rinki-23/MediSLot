const addBtn = document.getElementById("addBtn");
const slotList = document.getElementById("slotList");
const message = document.getElementById("message");
const profileId = localStorage.getItem("profileId");
const slotDate =  document.getElementById("slotDate");
const startTime = document.getElementById("startTime");
async function loadSlots() {
   
    try{
       const response =  await apiGet("/slot/available/" + profileId + "/" + slotDate.value);
       if(response === null){
        return;
       } 
       if(!response.ok){
        message.textContent = "Could not load slots";
        return;
       }

       const result = await response.json();

       result.sort(function(a, b){
        return a.startTime.localeCompare(b.startTime);
       }) 

       slotList.innerHTML = "";

       
       result.forEach(function(slot){
        const li = document.createElement("li");
        li.textContent = slot.startTime.substring(0,5);

        const delBtn = document.createElement("button");
        delBtn.textContent = "Delete";

        delBtn.addEventListener("click", async function(){
    if(!confirm("Delete this slot?")){
        return;
    }
    const res = await apiDelete("/slot/delete/" + slot.id);
    if(res === null){
        return;
    }
    if(res.ok){
        loadSlots();
    }else{
        message.textContent = "Could not delete slot"
    }
})


        li.appendChild(delBtn);
        slotList.appendChild(li);
       })

       
    }catch(err){
        console.log(err);
    }
}

addBtn.addEventListener("click", async function(){
    const body = {
        slotdate : slotDate.value,
        startTime : startTime.value

    }
     try{
       const response =  await apiPost("/slot/add/" + profileId,body);
        if(response === null){
            return;
        }
        if(response.ok){
            message.textContent = "slot Added Successfully";
            loadSlots();
        }else{
            const err = await response.json();
            message.textContent = err.message;
        }
    }catch(err){
        console.log(err);
    }
})

slotDate.addEventListener("change", loadSlots);
