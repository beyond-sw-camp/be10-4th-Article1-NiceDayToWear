<template>
  <div class="file-upload" @dragover.prevent @drop.prevent="handleDrop">
    <div class="label">
      <span>{{ label }}</span>
    </div>
    <input
        :id="inputId"
        type="file"
        @change="handleFileUpload"
        accept="image/*"
        multiple
        class="file-input"
    />
    <div class="drop-area" @click="triggerFileInput">
      <p>여기에 이미지를 드래그하거나 클릭하여 선택하세요.</p>
    </div>
    <div class="image-preview">
      <img v-for="(image, index) in images" :key="index" :src="image.src" alt="Uploaded image" />
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';

const props = defineProps({
  label: {
    type: String,
    required: true
  },
  imageList: {
    type: Array,
    default: () => []
  }
});

// emit 정의
const emit = defineEmits(['update:imageList']);

const inputId = `file-upload-${Math.random().toString(36).substr(2, 9)}`; // 고유 ID 생성
const images = ref([...props.imageList]); // 부모로부터 받은 이미지 리스트로 초기화

// 부모의 imageList가 변경될 때마다 images 업데이트
watch(() => props.imageList, (newList) => {
  images.value = newList;
});

function handleFileUpload(event) {
  const files = event.target.files; // 선택된 모든 파일
  handleFiles(files);
}

function handleDrop(event) {
  const files = event.dataTransfer.files; // 드롭된 파일
  handleFiles(files);
}

function handleFiles(files) {
  if (files.length > 0) {
    Array.from(files).forEach(file => {
      const reader = new FileReader();
      reader.onload = (e) => {
        // 원래 파일 이름과 데이터 URL을 함께 저장
        const imageData = {
          name: file.name, // 원래 파일 이름
          src: e.target.result // 데이터 URL
        };
        images.value.push(imageData); // 원래 파일 이름과 데이터 URL 객체를 배열에 추가
        emit('update:imageList', images.value); // 부모에게 이미지 리스트 전달
      };

      console.log("Uploaded file name:", file.name); // 파일 이름 로그 출력
      reader.readAsDataURL(file); // 파일을 데이터 URL로 읽기
    });
  }
}

function triggerFileInput() {
  const fileInput = document.getElementById(inputId);
  fileInput.click(); // 클릭 시 파일 선택 대화상자 열기
}
</script>

<style scoped>
.file-upload {
  margin-top: 20px; /* 여백 추가 */
}

.label {
  font-size: 20px;
  font-weight: bold;
}

.file-input {
  display: none; /* 파일 입력 숨기기 */
}

.drop-area {
  border: 2px dashed #ccc; /* 테두리 스타일 */
  border-radius: 4px; /* 둥글게 */
  padding: 20px; /* 여백 추가 */
  text-align: center; /* 가운데 정렬 */
  cursor: pointer; /* 커서 스타일 변경 */
  margin-top: 10px; /* 상단 여백 */
}

.drop-area:hover {
  border-color: #888; /* 드래그 시 색상 변경 */
}

.image-preview {
  margin-top: 20px; /* 상단 여백 */
  display: flex; /* Flexbox 사용 */
  flex-wrap: wrap; /* 줄 바꿈 */
}

.image-preview img {
  max-width: 100px; /* 최대 너비 설정 */
  margin-right: 10px; /* 이미지 간의 간격 */
  margin-bottom: 10px; /* 이미지 하단 간격 */
}
</style>
