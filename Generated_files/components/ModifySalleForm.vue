<script setup>
    import {ref} from 'vue'
    import router from '../router'
    import { defineProps } from 'vue'

    const props = defineProps({
        id:String
    })

    var idsalle = ref(props.id)
    var nom = ref('')

    function onChangeNom(event){
        nom = event.target.value
        console.log(nom)
    }

    async function fetchSalleById(){
        const url = 'http://localhost:8080/salles/'+idsalle.value;
        console.log(url);

        try{
            const response = await fetch(url,{
                method : 'GET',
                headers : {
                    'Content-Type' : 'application/json'
                }
            });
            const data = await response.json();
            console.log(data);
            nom.value = data.nom;
        }catch(err){
            console.log(err.message);
        }
    }

    async function onSubmit(e){
        e.preventDefault();
        const url = 'http://localhost:8080/salles/'+idsalle.value;
        console.log(url);

        try{
            const response = await fetch(url,{
                method : 'PUT',
                headers : {
                    'Content-Type' : 'application/json'
                },
                body : JSON.stringify({'idsalle' : parseInt(idsalle.value),'nom' : nom.value}).replace(/\\/g, '').replace(/"({.*})"/g, '$1')
            })
            const data = await response.json();
            console.log(data);
            router.push('/list_salles')
        }catch(err){
            console.log(err.message);
        }
    }
    onMounted(() => {
        fetchSalleById(idsalle)
    })

</script>

<template>
    <form>
        <label for="nom">nom : </label><input @change="onChangeNom" type="text" name="nom"/>
        <button  @click="onSubmit">submit</button>
    </form>
</template>