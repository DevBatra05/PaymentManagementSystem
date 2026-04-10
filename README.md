# Alzheimer's Disease Severity Detection Using VGG16
### Deep Learning | Transfer Learning | 99.92% Test Accuracy

A deep learning pipeline that classifies MRI brain scans into four stages of Alzheimer's disease using VGG16 transfer learning with fine-tuning, trained on 44,000 images.

---

## Results at a Glance

| Metric | Score |
|---|---|
| Test Accuracy | **99.92%** |
| Dataset Size | 44,000 MRI images |
| Classes | 4 Alzheimer's stages |
| Base Model | VGG16 (ImageNet weights) |
| Fine-tuned Layers | Last 15 layers |
| Inference Speed | ~11 images/second |
| Training Platform | Google Colab (T4 GPU) |

---

## Problem Statement

Alzheimer's disease is a progressive neurological disorder affecting millions globally. Early and accurate detection is critical for treatment planning. This project builds an automated MRI classification system that distinguishes between four stages of cognitive decline using deep learning — eliminating the need for time-consuming manual diagnosis.

---

## Dataset

**Source:** [Alzheimer's Multiclass Dataset — Kaggle](https://www.kaggle.com/datasets/aryansinghal10/alzheimers-multiclass-dataset-equal-and-augmented)

| Class | Description |
|---|---|
| NonDemented | No signs of Alzheimer's |
| VeryMildDemented | Very early stage |
| MildDemented | Mild cognitive impairment |
| ModerateDemented | Moderate cognitive impairment |

```python
import kagglehub
path = kagglehub.dataset_download("aryansinghal10/alzheimers-multiclass-dataset-equal-and-augmented")
```

---

## Model Architecture

```
Input (224 × 224 × 3)
  └── VGG16 Base (ImageNet pretrained)
        ├── First layers → Frozen
        └── Last 15 layers → Fine-tuned
  └── GlobalAveragePooling2D
  └── BatchNormalization
  └── Dense(512, relu) + Dropout(0.5)
  └── Dense(256, relu) + Dropout(0.3)
  └── Dense(4, softmax)
```

---

## Training Configuration

| Parameter | Value |
|---|---|
| Optimizer | Adam |
| Loss | Categorical Crossentropy |
| Batch Size | 32 |
| Image Size | 224 × 224 |
| Callbacks | ModelCheckpoint, EarlyStopping, ReduceLROnPlateau |
| Seed | 42 |

---

## Evaluation Methods

- Confusion Matrix
- Classification Report (precision, recall, F1)
- ROC-AUC Curves
- Grad-CAM Heatmaps (explainability)

---

## Project Structure

```
Alzheimers-MRI-Detection/
├── Code_file.ipynb                                        # Full notebook
├── Alzheimer Disease Severity Detection Using VGG16.pdf   # Report
├── Alzheimer Disease Severity Detection Using VGG16.pptx  # Slides
└── README.md
```

---

## Setup & Running

### Google Colab (recommended)
1. Open `Code_file.ipynb` in Colab
2. Runtime → Change runtime type → **T4 GPU**
3. Run all cells

### Local
```bash
pip install tensorflow kagglehub scikit-learn seaborn matplotlib
jupyter notebook Code_file.ipynb
```

---

## Pretrained Model

**[Download from Google Drive](https://drive.google.com/file/d/13ZyfL-Z5N_-kOOKlDa_8V551sDmkmYLX/view?usp=sharing)**

```python
from tensorflow.keras.models import load_model
model = load_model("alzheimers_vgg16.h5")
```

---

## Inference Example

```python
from tensorflow.keras.preprocessing import image
from tensorflow.keras.applications.vgg16 import preprocess_input
import numpy as np

CLASS_NAMES = ['MildDemented', 'ModerateDemented', 'NonDemented', 'VeryMildDemented']

img  = image.load_img("your_mri.jpg", target_size=(224, 224))
x    = preprocess_input(np.expand_dims(image.img_to_array(img), axis=0))
pred = model.predict(x)

print(f"Predicted : {CLASS_NAMES[np.argmax(pred)]}")
print(f"Confidence: {np.max(pred)*100:.2f}%")
```

---

## Tech Stack

| Category | Tools |
|---|---|
| Deep Learning | TensorFlow 2.20, Keras |
| Base Model | VGG16 (ImageNet) |
| Evaluation | scikit-learn, Grad-CAM |
| Visualization | matplotlib, seaborn |
| Platform | Google Colab T4 GPU |

---

## Key Findings

- Fine-tuning last 15 VGG16 layers prevented catastrophic forgetting of ImageNet features
- Grad-CAM confirmed model focuses on medically relevant brain regions
- Class weights balanced training effectively across all 4 stages
- ~11 images/second inference speed — suitable for clinical screening use
