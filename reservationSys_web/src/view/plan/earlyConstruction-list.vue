<template>
    <div class="container">
        <planList :projectType="projectType"></planList>
    </div>
</template>
<script>
import planList from './component/planList';
export default {
    name:'earlyConstruction-list', 
    data(){
        return{
            projectType:3
        }
    },
    components:{
        planList
    },
    mounted(){
        
    },
    methods:{
    }
}
</script>
<style lang="scss" scoped>
</style>