<script setup>
    import {onMounted , ref} from 'vue'
    import router from '../router'

    var salles = ref([])

    async function fetchSalles(){
        const url = 'http://localhost:8080/salles';
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
            salles.value = data;
            console.log(salles.value)
        }catch(err){
            console.log(err.message);
        }
    }

    onMounted(() => {
        fetchSalles()
    })

    function onClickModify(event){
        console.log(salles.value)
        console.log("/modify_salle/"+salles.value[event.target.value].idsalle);
        router.push("/modify_salle/"+salles.value[event.target.value].idsalle);
    }
</script>

<template>
    <ul>
        <li v-for="(salle,index) in salles" :key="index">
            {{ console.log(index) }}
            {{ salle.nom }}  <button @click="onClickModify" :value="index">modify</button> <button>delete</button>
        </li>
    </ul></template>