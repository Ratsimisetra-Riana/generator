<script setup>
    import {onMounted , ref} from 'vue'
    import router from '../router'

    var examens = ref([])

    async function fetchExamens(){
        const url = 'http://localhost:8080/examens';
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
            examens.value = data;
            console.log(examens.value)
        }catch(err){
            console.log(err.message);
        }
    }

    onMounted(() => {
        fetchExamens()
    })

    function onClickModify(event){
        console.log(examens.value)
        console.log("/modify_examen/"+examens.value[event.target.value].idexamen);
        router.push("/modify_examen/"+examens.value[event.target.value].idexamen);
    }
    async function onClickDelete(event){
        const url = 'http://localhost:8080/examens/'+examens.value[event.target.value].idexamen;
        console.log(url);

        try{
            await fetch(url,{
                method : 'DELETE',
                headers : {
                }
            });
        }catch(err){
            console.log(err.message);
        }
    }

</script>

<template>
    <ul>
        <li v-for="(examen,index) in examens" :key="index">
            {{ console.log(index) }}
            {{ examen.salle.nom }} {{ examen.dateexamen }}  <button @click="onClickModify" :value="index">modify</button> <button @click="onClickDelete" :value="index">delete</button>
        </li>
    </ul></template>