export function render(content) {
    return `
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Soup Kitchen admin</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                background-color: #f4f4f4;
            }
            .container {
                width: 100%;
                max-width: 400px;
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                text-align: center;
                margin-top: 20px;
            }
            .container h1 {
                margin-bottom: 20px;
                font-size: 24px;
                color: #333;
            }
            .file-input-wrapper {
                position: relative;
                display: inline-block;
                margin-bottom: 15px;
            }
            .file-input {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                opacity: 0;
                cursor: pointer;
            }
            .custom-file-button {
                background: #007BFF;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.2s;
            }
            .custom-file-button:hover {
                background: #0056b3;
            }
            .upload-button {
                background: #4CAF50;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.2s;
            }
            .upload-button:hover {
                background: #45a049;
            }
            .upload-button:disabled {
                background: #ddd;
                cursor: not-allowed;
            }
            .error-message {
                color: red;
                font-size: 14px;
                margin-top: 15px;
                display: none;
            }
            .thumbnails-container {
                display: flex;
                gap: 10px;
                justify-content: center;
                margin-top: 10px;
            }
            .thumbnail-wrapper {
                text-align: center;
                font-size: 14px;
                color: #555;
            }
            .thumbnail {
                width: 100px;
                height: 100px;
                object-fit: cover;
                cursor: pointer;
                border: 2px solid #ddd;
                border-radius: 4px;
                transition: transform 0.2s, border-color 0.2s;
            }
            .thumbnail:hover {
                transform: scale(1.1);
                border-color: #4CAF50;
            }
            .modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.8);
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }
            .modal img {
                max-width: 90%;
                max-height: 90%;
                border-radius: 8px;
            }
            .modal-close {
                position: absolute;
                top: 20px;
                right: 20px;
                font-size: 24px;
                color: white;
                cursor: pointer;
            }
            .password-input {
                padding: 10px 15px;
                width: 80px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                margin-top: 10px;
                margin-right: 10px;
                margin-left: 10px;
            }
        </style>
    </head>
    <body>
    <img src="https://outsideworx.net/assets/img/portfolio/zinis.png" width="600"/>
    <div class="container">
        <h1>Override a Menu item</h1>
        <form id="uploadForm" action="upload.php" method="post" enctype="multipart/form-data">
            <div class="file-input-wrapper">
                <button type="button" class="custom-file-button">Select File</button>
                <input type="file" name="uploadedFile" class="file-input" required>
            </div>
            <input type="password" id="passwordInput" class="password-input" placeholder="Enter PIN" required>
            <button type="submit" class="upload-button" disabled>Upload</button>
            <p class="error-message" id="errorMessage">Invalid file name. Only menu.webp, menu1.webp, or menu2.webp are allowed.</p>
        </form>
    </div>
    
    <div class="container">
        <h1>Current Menu</h1>
        <div class="thumbnails-container">
            <div class="thumbnail-wrapper">
                <img src="data:image/webp;base64,${content[0]?.desktop}" alt="Desktop: menu.webp" class="thumbnail" onclick="openModal(this.src)">
                <p>Desktop:<br/>menu.webp</p>
            </div>
            <div class="thumbnail-wrapper">
                <img src="data:image/webp;base64,${content[0]?.mobile1}" alt="Mobile: menu1.webp" class="thumbnail" onclick="openModal(this.src)">
                <p>Mobile:<br/>menu1.webp</p>
            </div>
            <div class="thumbnail-wrapper">
                <img src="data:image/webp;base64,${content[0]?.mobile2}" alt="Mobile: menu2.webp" class="thumbnail" onclick="openModal(this.src)">
                <p>Mobile:<br/>menu2.webp</p>
            </div>
        </div>
    </div>
    
    <div id="imageModal" class="modal">
        <span class="modal-close" onclick="closeModal()">&times;</span>
        <img id="modalImage" src="" alt="Expanded Image">
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        const allowedFiles = ['menu.webp', 'menu1.webp', 'menu2.webp'];
        const fileInput = document.querySelector('.file-input');
        const customButton = document.querySelector('.custom-file-button');
        const uploadForm = document.getElementById('uploadForm');
        const errorMessage = document.getElementById('errorMessage');
        const passwordInput = document.getElementById('passwordInput');
        const uploadButton = document.querySelector('.upload-button');
        
        // Enable upload button only if password is entered
        passwordInput.addEventListener('input', () => {
            uploadButton.disabled = passwordInput.value.trim() === '' || customButton.textContent === 'Select File' || errorMessage.style.display === 'block';
        });
        
        // Update custom button text when file is selected
        fileInput.addEventListener('change', () => {
            const fileName = fileInput.files[0]?.name || 'Select File';
            customButton.textContent = fileName;
        
            // Validate file name
            if (!allowedFiles.includes(fileName)) {
                errorMessage.style.display = 'block';
                uploadButton.disabled = true;
            } else {
                errorMessage.style.display = 'none';
                uploadButton.disabled = passwordInput.value.trim() === '';
            }
        });
        
        // Prevent form submission if file name is invalid
        uploadForm.addEventListener('submit', (e) => {
            const fileName = fileInput.files[0]?.name;
        
            if (!allowedFiles.includes(fileName)) {
                e.preventDefault();
                errorMessage.style.display = 'block';
            }
        });
        
        // Open Modal
        function openModal(src) {
            const modal = document.getElementById('imageModal');
            const modalImage = document.getElementById('modalImage');
            modalImage.src = src;
            modal.style.display = 'flex';
        }
        
        // Close Modal
        function closeModal() {
            const modal = document.getElementById('imageModal');
            modal.style.display = 'none';
        }
    </script>
    <script>
        $(document).ready(function() {
            $('#uploadForm').on('submit', function(e) {
                e.preventDefault();
                var formData = $(this).serialize();
                $.ajax({
                    type: 'POST',
                    url: 'https://outsideworx.net/admin/soupkitchen',
                    data: formData,
                    success: function(response) {
                        alert('Form submitted successfully!');
                        console.log(response);
                    },
                    error: function(xhr, status, error) {
                        alert('An error occurred. Please try again later.');
                        console.error(error);
                    }
                });
            });
        });
    </script>
    </body>
    </html>
`;
}
