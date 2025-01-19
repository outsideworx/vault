import * as soupkitchen from './soupkitchen';

const moduleMap = {
    soupkitchen: soupkitchen.render
};

function lastPathSegment(request) {
    const url = new URL(request.url);
    const pathSegments = url.pathname.split('/').filter(Boolean);
    return pathSegments[pathSegments.length - 1];
}

export default {
    async fetch(request, env) {
        const {DATABASE} = env;
        const pathSegment = lastPathSegment(request);
        const stmt = DATABASE.prepare('SELECT * FROM '.concat(pathSegment));
        const {results} = await stmt.all();

        return new Response(
            moduleMap[pathSegment](JSON.stringify(results)),
            {
                headers: {
                    'content-type': 'text/html'
                },
            }
        )
    }
}
