<template>
    <div class="container">
        <reviewList :stage="stage"></reviewList>
    </div>
</template>
<script>
import reviewList from './component/reviewlist-model';
export default {
    name:'reviewlist-company',
    data(){
        return{
            stage:3
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

