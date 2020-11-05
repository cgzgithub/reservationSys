<template>
    <div class="container">
        <planList :projectType="projectType"></planList>
    </div>
</template>
<script>
import planList from './component/planList';
export default {
    name:'landTransfer-list', 
    data(){
        return{
            projectType:2
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