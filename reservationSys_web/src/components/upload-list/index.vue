<template>
  <div>
    <el-upload
      ref="elUpload"
      class="upload-list"
      multiple
      :action="action"
      :headers="headers"
      :file-list="fileList"
      :list-type="listType"
      :show-file-list="true"
      :on-success="handleSuccess"
      :on-remove="handelRemove"
      :on-error="handleError"
      :before-upload="beforeUpload"
      :on-preview="handlePreview"
      :data="extData"
    >
      <i class="el-icon-plus" v-if="listType === 'picture-card'"></i>
      <el-button size="mini" type="primary" v-else>上传</el-button>

      <div slot="tip" class="el-upload__tip" v-if="tip">{{tip}}</div>
    </el-upload>
    <Modal title="图片详情" v-model="dialogVisible">
        <img :src="currentImg" alt class="preview-img">
    </Modal>
  </div>
</template>

<script>
// import imgUtil from '@/utils/img';
import auth from '@/service/auth';

export default {
  name: 'upload-list',
  props: {
    value: {
      type: Array,
      default() {
        return [];
      }
    },
    name: {
      type: String,
      default: 'file'
    },
    action: {
      type: String,
      default: `/api/file/upload`
    },
    fileSize: {
      type: Number,
      default: 512
    },
    fileType: {
      type: Array,
      default() {
        return [];
      }
    },
    listType: {
      type: String,
      default: 'text'
    },
    tip: {
      type: String,
      default: ''
    },
    storeType: {
      type: String,
      default: 'other'
    }
  },
  data() {
    return {
      fileList: [],
      headers:{
        'session_channel':'MANAGER'
      },
      extData: {
        token: auth.getToken(),
        type: this.storeType
      },
      loading: false,
      currentImg: '',
      dialogVisible: false
    };
  },
  watch: {
    value: {
      handler(val) {
        if(val){
          val.forEach(function(item){
              item.name = item.fileName
              item.url = item.url != undefined?item.url:item.downloadUrl;
          })
        }
        this.fileList = val || [];
      },
      immediate: true,
      deep: true
    },
    fileList: {
      handler(val) {
        this.$emit('input', val);
      },
      deep: true
    }
  },
  methods: {
    closeLoading() {
      this.loading = false;
    },
    handleSuccess(res, file) {
      this.closeLoading();
      const { status, data, msg } = res;
      if (status === 1) {
        // file.id = data;
        file.fileUrl = data;
        file.fileName = file.name;
        // if (data.fid) {
        //   file.id = data.fid;

        //   this.$emit('parse', data.summary || '');
        // }
      } else {
        this.$Message.error(msg || '文件上传出错，请稍后重试！');
      }

      this.fileList.push(file);
    },
    handleError(err, file, fileList) {
      this.$Message.error(err.message || '文件上传失败，请稍后重试！');
    },
    beforeUpload(file) {
      // const { fileSize, fileType, listType } = this;
      // const isPic = fileType.indexOf(file.type) > -1;
      // const isLt5k = file.size / 1024  <= fileSize;
      // const islt20M = file.size / 1024 /1024  <= fileSize;
      // const isLt2M = (listType == 'picture-card')?isLt5k:islt20M
      // let flag = false;
      // const self = this;

      // if (!isPic) {
      //   this.$message.error('上传文件格式错误!');
      // }
      // if (!isLt2M) {
      //   // const unit = fileSize < 1 ? `${fileSize * 1000}KB` : `${fileSize}kB`;
      //   const unit = (listType == 'picture-card')?`${fileSize}kB`:`${fileSize}MB`
      //   this.$message.error(`上传文件大小不能超过${unit}!`);
      // }

      // flag = isPic && isLt2M;

      // if (!flag) {
      //   return flag;
      // } else if (listType !== 'picture-card') {
      //   this.loading = true;
      //   return flag;
      // } else {
      //   const uploadFiles = self.$refs.elUpload.uploadFiles;
      //   const currentFileIndex = uploadFiles.findIndex(
      //     item => item.uid === file.uid
      //   );
      //   const currentFile = uploadFiles[currentFileIndex];

      //   this.loading = true;
      //   return new Promise(resolve => {
      //     imgUtil.getOrientation(file).then(orient => {
      //       if (orient && orient !== '' && orient !== 1) {
      //         currentFile.url = '';

      //         let reader = new FileReader();
      //         let img = new Image();
      //         reader.onload = e => {
      //           img.src = e.target.result;
      //           img.onload = function() {
      //             const data = imgUtil.rotateImage(
      //               img,
      //               img.width,
      //               img.height,
      //               orient
      //             );
      //             const newFile = imgUtil.dataURLtoFile(data, file.name);

      //             currentFile.url = URL.createObjectURL(newFile);

      //             uploadFiles.splice(currentFileIndex, 1, currentFile);
      //             for (const p in newFile) {
      //               if (newFile.hasOwnProperty(p)) {
      //                 file[p] = newFile[p];
      //               }
      //             }
      //             file.url = URL.createObjectURL(newFile);
      //             resolve(newFile);
      //           };
      //         };
      //         reader.readAsDataURL(file);
      //       } else {
      //         resolve(file);
      //       }
      //     });
      //   });
      // }
    },

    handelRemove(file, fileList) {
      const fileId = file.fileUrl;

      for (let index = this.fileList.length - 1; index >= 0; index--) {
        const element = this.fileList[index];

        if (element.fileUrl === fileId) {
          this.fileList.splice(index, 1);
        }
      }
    },

    handlePreview(file) {
      if(this.listType == 'text'){
        // location.href = file.downloadUrl;
        window.open(file.downloadUrl)
      }else{
        if (file.url) {
          this.dialogVisible = true;
          this.currentImg = file.url;
        }
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.preview-img {
  width: 100%;
}
</style>

