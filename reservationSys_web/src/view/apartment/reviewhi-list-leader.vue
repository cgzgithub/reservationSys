<template>
    <div class="container">
        <reviewhiList :stage="stage"></reviewhiList>
    </div>
</template>
<script>
import reviewhiList from './component/reviewhi-list-model';
export default {
    name:'reviewhi-list-leader',
    data(){
        return{
            stage:4
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

