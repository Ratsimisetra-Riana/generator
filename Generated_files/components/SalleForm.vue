<script setup>
    import {ref} from 'vue'
    import router from '../router'

    var nom = ref('')

    function onChangeNom(event){
        nom = event.target.value
        console.log(nom)
    }

    async function onSubmit(e){
        e.preventDefault();
        const url = 'http://localhost:8080/salles';
        console.log(url);

        try{
            const response = await fetch(url,{
                method : 'POST',
                headers : {
                    'Content-Type' : 'application/json'
                },
                body : JSON.stringify({'nom' : nom.value}).replace(/\\/g, '').replace(/"({.*})"/g, '$1')
            })
            const data = await response.json();
            console.log(data);
            router.push('/list_salles')
        }catch(err){
            console.log(err.message);
        }
    }
    onMounted(() => {
    })

</script>

<template>
    <form>
        <label for="nom">nom : </label><input @change="onChangeNom" type="text" name="nom"/>
        <button  @click="onSubmit">submit</button>
    </form>
</template>