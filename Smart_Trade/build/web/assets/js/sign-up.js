async function signUP() {

    const user_dto = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
    };


    const response = await fetch(
        "SignUp",
        {
            method: "POST",
            body: JSON.stringify(user_dto),
            headers: {
                "Content-Type": "application/json"
            }
        }
    );

    if (response.ok) {
        const json = await response.json();
        // Handle the JSON response
        console.log(json);
        
        if (json.success){
            
            window.location = "verify-account.html";
            
        }else{
             document.getElementById("message").innerHTML = json.content;
        }
        
    } else {
       
  
        document.getElementById("message").innerHTML = "Please try again Later ";
    }
}
