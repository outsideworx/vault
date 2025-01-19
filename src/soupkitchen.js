export function render(content) {
    return `
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Soup Kitchen admin</title>
        <link rel="stylesheet" type="text/css" href="https://static.integrations.cloudflare.com/styles.css">
      </head>
      <body>
        <header>
          <img src="https://outsideworx.net/assets/img/portfolio/zinis.png" width="400"/>
          <h1>🎉 Successfully connected to SOUPKITCHEN admin site.</h1>
        </header>
        <main>
          <code><span style="color: #0E838F">&gt; </span>SELECT * FROM soupkitchen;<br/><br/>${content}</code>
        </main>
      </body>
    </html>
`;
}
