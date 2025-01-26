import * as soupkitchen from './soupkitchen';

const moduleMap = {
    soupkitchen: {
        render: soupkitchen.render,
        query: `SELECT t.* FROM soupkitchen t
                JOIN (SELECT MAX(timestamp) AS latest_timestamp, menu, menu_mobile FROM soupkitchen GROUP BY menu, menu_mobile) 
                latest ON t.timestamp = latest.latest_timestamp AND t.menu = latest.menu AND t.menu_mobile = latest.menu_mobile;`,
        update: `INSERT INTO soupkitchen (timestamp, ${1}) VALUES (${0}, '${2}')`
    }
};

function lastPathSegment(request) {
    const url = new URL(request.url);
    const pathSegments = url.pathname.split('/').filter(Boolean);
    return pathSegments[pathSegments.length - 1];
}

function arrayBufferToBase64(buffer) {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const length = bytes.length;
    for (let i = 0; i < length; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary);
}

export default {
    async fetch(request, env) {
        const {DATABASE} = env;
        const {method} = request;
        const pathSegment = lastPathSegment(request);

        if (!(pathSegment in moduleMap)) {
            console.log('Path segment is not in the module map.');
            return new Response('Not Found.', {status: 404});
        }
        if (method === "POST") {
            const formData = await request.formData();
            const passwordInput = formData.get('passwordInput');
            if (passwordInput === "1132") {
                const uploadedFile = formData.get('uploadedFile');
                const fileBuffer = await uploadedFile.arrayBuffer();
                const base64 = arrayBufferToBase64(fileBuffer);
                const statement = DATABASE.prepare(moduleMap[pathSegment]["query"].format(Date.now(), uploadedFile.name.split('.')[0], base64));
                const result = await statement.run();
                return new Response(result);
            }
            return new Response('Forbidden.', {status: 403});
        }

        const statement = DATABASE.prepare(moduleMap[pathSegment]["query"]);
        const {results} = await statement.all();
        return new Response(moduleMap[pathSegment]["render"](results), {headers: {'content-type': 'text/html'}});
    }
}
