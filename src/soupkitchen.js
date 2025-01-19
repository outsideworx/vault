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
          <h1>🎉 Successfully connected to Soup Kitchen admin site.</h1>
        </header>
        <main>
          <code><span style="color: #0E838F">&gt; </span>SELECT timestamp FROM soupkitchen LIMIT 1;<br/><br/>${content[0].timestamp}</code>
        </main>
      </body>
    </html>
`;
}
