document.getElementById("form-add").addEventListener("submit", function (event){
    event.preventDefault();
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();

    if(!name || !email){
    window.alert("Preencha todos os campos!");
    return;
    }

    const userData = {name, email};
    createUser(userData)
})

async function createUser(userData) {
    try {
      const response = await fetch("http://localhost:8090/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
      })
      if(!response.ok){
      console.log("response not ok!")
      return;
      }
      window.alert("Usuário criado!");
      window.location.href = "/html/listUsers.html"
    }catch (error) {
      console.log("Falha ao criar usuario: ", error);
    }
}