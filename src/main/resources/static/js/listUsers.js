document.addEventListener("load", listUsers());

async function listUsers(){
  try {
    const response = await fetch("http://localhost:8090/users", {
      method: "GET",
    });
        if (!response.ok) {
          window.alert("response not ok!");
          return;
        }
        const data = await response.json();
        const usersContainer = document.getElementById('users-container');
        usersContainer.innerHTML = '';

        if (Array.isArray(data) && data.length > 0){
            data.forEach(user => {
                const userElement = document.createElement('div');
                userElement.classList.add('user-card');
                userElement.innerHTML = `
                <h3>${user.name}</h3>
                <p>Email: <span>${user.email}</span></p>
                <p>Id: <span>${user.id}</span></p>
                <button class="delete_btn" onclick="deleteUser('${user.id}')"><i class="fa-solid fa-trash-can"></i>Excluir</button>
                <button class="edit_btn" onclick="mvToEditUser('${user.id}')"><i class="fa-solid fa-pen-to-square"></i>Editar</button>
                `;
                usersContainer.appendChild(userElement);
            });
        }else{
            usersContainer.innerHTML = '<p style="color:white; font-size:32px;">Nenhum usuário encontrado.</p>'
        }
  }catch(error) {
    console.alert("Falha ao buscar usuarios...");
  }
}

function mvToEditUser(id){
    window.location.href = "editUser.html?id=" + id;
}