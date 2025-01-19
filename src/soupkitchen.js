export function render(content) {
    return `
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Soup Kitchen admin</title>
        <link rel="stylesheet" href="style/admin.css">
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
                <img src="data:image/webp;base64,${content[0].desktop}" alt="Desktop: menu.webp" class="thumbnail" onclick="openModal(this.src)">
                <p>Desktop:<br/>menu.webp</p>
            </div>
            <div class="thumbnail-wrapper">
                <img src="data:image/webp;base64,${content[0].mobile1}" alt="Mobile: menu1.webp" class="thumbnail" onclick="openModal(this.src)">
                <p>Mobile:<br/>menu1.webp</p>
            </div>
            <div class="thumbnail-wrapper">
                <img src="data:image/webp;base64,${content[0].mobile2}" alt="Mobile: menu2.webp" class="thumbnail" onclick="openModal(this.src)">
                <p>Mobile:<br/>menu2.webp</p>
            </div>
        </div>
    </div>
    
    <div id="imageModal" class="modal">
        <span class="modal-close" onclick="closeModal()">&times;</span>
        <img id="modalImage" src="" alt="Expanded Image">
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="script/admin.js"></script>
    <script src="script/form-submit.js"></script>
    </body>
    </html>
`;
}
