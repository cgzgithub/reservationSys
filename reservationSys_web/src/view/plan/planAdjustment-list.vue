<template>
    <div class="container">
        <planList :projectType="projectType"></planList>
    </div>
</template>
<script>
import planList from './component/planList';
export default {
    name:'planAdjustment-list', 
    data(){
        return{
            projectType:1
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