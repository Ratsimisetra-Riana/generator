<script setup>
    import {onMounted , ref} from 'vue'
    import router from '../router'

    var salles = ref([])
    var selected_salle = ref({})
    var dateexamen = ref('')

    function onChangeSelectedsalle(event){
        selected_salle.value =salles.value[event.target.value]
        console.log(selected_salle)
    }

    function onChangeDateexamen(event){
        dateexamen.value = event.target.value
        console.log(dateexamen)
    }

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

    async function onSubmit(e){
        e.preventDefault();
        const url = 'http://localhost:8080/examens';
        console.log(url);

        try{
            const response = await fetch(url,{
                method : 'POST',
                headers : {
                    'Content-Type' : 'application/json'
                },
                body : JSON.stringify({'salle' : JSON.stringify(selected_salle.value),'dateexamen' : dateexamen.value}).replace(/\\/g, '').replace(/"({.*})"/g, '$1')
            })
            const data = await response.json();
            console.log(data);
            router.push('/list_examens')
        }catch(err){
            console.log(err.message);
        }
    }
    onMounted(() => {
        fetchSalles()
    })

</script>

<template>
    <form>
        <select name="salles">
            <option v-for="(salle,index) in salles" :key="index" name="salles" :value="index" @click="onChangeSelectedsalle">
                {{ console.log(index) }}
                {{ salle.nom }}
            </option>
        </select>
        <label for="dateexamen">dateexamen : </label><input @change="onChangeDateexamen" type="text" name="dateexamen"/>
        <button  @click="onSubmit">submit</button>
    </form>
</template>