<template>
    <div class="container">
        <reviewList :stage="stage"></reviewList>
    </div>
</template>
<script>
import reviewList from './component/reviewlist-model';
export default {
    name:'reviewlist-leader',
    data(){
        return{
            stage:4
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

