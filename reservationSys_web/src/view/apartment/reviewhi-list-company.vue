<template>
    <div class="container">
        <reviewhiList :stage="stage"></reviewhiList>
    </div>
</template>
<script>
import reviewhiList from './component/reviewhi-list-model';
export default {
    name:'reviewhi-list-company',
    data(){
        return{
            stage:3
        }
    },
    components:{
        reviewhiList
    },
    mounted(){
    },
    methods:{
    }
}
</script>

