<template>
    <div class="container">
        <reviewList :stage="stage"></reviewList>
    </div>
</template>
<script>
import reviewList from './component/reviewlist-model';
export default {
    name:'reviewlist-self',
    data(){
        return{
            stage:1
        }
    },
    components:{
        reviewList
    },
    mounted(){
    },
    methods:{
    }
}
</script>

