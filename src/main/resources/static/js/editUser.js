window.onload = async function () {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        window.alert("Usuário não encontrado!")
        return;
    }

    try {
        const response = await fetch(`http://localhost:8090/users/${id}`);
        if (!response.ok) {
         console.log("response not ok!")
         return;
        }

        const userData = await response.json();
        console.log(userData);
        populateForm(userData);
    } catch (error) {
        console.alert("Falha ao buscar dados do usuario...");
    }
}

function populateForm(userData){
    document.getElementById("name").value = userData.name || "";
    document.getElementById("email").value = userData.email || "";
    document.getElementById("id").value = userData.id || "";
}

document.getElementById("form-edit").addEventListener("submit", function (event) {
    event.preventDefault();
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const id = document.getElementById("id").value.trim();
    if(!name || !email){
    window.alert("Preencha todos os campos adequadamente!");
    return;
    }
    const newUser = {id,name,email};
    updateUser(newUser);

});

async function updateUser(newUser){
    try{
      const id = newUser.id;
        const response = await fetch(`http://localhost:8090/users/${id}`,{
        method: "PUT",
        headers: {
        "Content-Type": "application/json",},
           body:JSON.stringify(newUser),
         });
        if(!response.ok){
             window.alert("response not ok!");
          return;
        }
        window.alert("usuario atualizado com sucesso!");
        window.location.href="listUsers.html"
    }catch(error){
    window.alert("Falha ao atualizar dados...")
    }

}
