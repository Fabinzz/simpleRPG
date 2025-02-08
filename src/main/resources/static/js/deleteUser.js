async function deleteUser(id){
    try{
        const response = await fetch(`http://localhost:8090/users/${id}`,{method: 'DELETE'});
            if(!response.ok){
            console.log("response not ok!")
            return;
            }
        console.log("user gone!");
        window.alert("usuario apagado!")
        listUsers();
    }
    catch(error){
    console.log("Falha ao deletar usuario...");
    }
}