
//Base address of our spring boot backend
const BASE_URL = "http://localhost:8080";

// sends a get request with the saved token
async function apiGet(path){

    // read the token that was saved at login time
    const token = localStorage.getItem("token");

    // no token means user is not logged in , so go to login page
    if(!token){
        window.location.href = "login.html";
        return null;
    }

    // send request with the authorization header
    const response = await fetch(BASE_URL + path, {
    headers : { "Authorization" : "Bearer " + token}
});

// 401 means token is missing, wrong or expired , so login again
if(response.status === 401){
    localStorage.clear();
    window.location.href = "login.html";
    return null;
}

// give the response back to the page that called us
return response;
}

async function apiPost(path){

     // read the token that was saved at login time
    const token = localStorage.getItem("token");

    // no token means user is not logged in , so go to login page
    if(!token){
        window.location.href = "login.html";
        return null;
    }

    const response = await fetch(BASE_URL + path, {
        method : "POST",
     headers : { "Authorization" : "Bearer " + token}
});

// 401 means token is missing, wrong or expired , so login again
if(response.status === 401){
    localStorage.clear();
    window.location.href = "login.html";
    return null;
}

// give the response back to the page that called us
return response;
}


async function apiPut(path){

     // read the token that was saved at login time
    const token = localStorage.getItem("token");

    // no token means user is not logged in , so go to login page
    if(!token){
        window.location.href = "login.html";
        return null;
    }

    const response = await fetch(BASE_URL + path, {
        method : "PUT",
     headers : { "Authorization" : "Bearer " + token}
});

// 401 means token is missing, wrong or expired , so login again
if(response.status === 401){
    localStorage.clear();
    window.location.href = "login.html";
    return null;
}

// give the response back to the page that called us
return response;
}




