<template>
    <div class="container">
        <reviewhiList :stage="stage"></reviewhiList>
    </div>
</template>
<script>
import reviewhiList from './component/reviewhi-list-model';
export default {
    name:'reviewhi-list-self',
    data(){
        return{
            stage:1
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

