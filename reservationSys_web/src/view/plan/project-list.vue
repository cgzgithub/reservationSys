<template>
    <div class="container">
        <planList :projectType="projectType"></planList>
    </div>
</template>
<script>
import planList from './component/planList';
export default {
    name:'project-list', 
    data(){
        return{
            projectType:0
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