
const registerForm = document.getElementById("registerForm");

if (registerForm) {
    registerForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("http://localhost:8081/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    name: name,
                    email: email,
                    password: password
                })
            });

            const result = await response.text();
            alert(result);
        } catch (error) {
            alert("Could not connect to server. Please check if it is running.");
        }
    });
}

const loginForm = document.getElementById("loginForm");

if (loginForm) {
    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("http://localhost:8081/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

           const result = await response.json();

if (response.ok && result.id) {
    localStorage.setItem("studentId", result.id);
    window.location.href = "dashboard.html";
} else {
    alert("Login failed. Please check your details.");
} 

    } catch (error) {
            alert("Could not connect to server. Please check if it is running.");
        }
    });
}
