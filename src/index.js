import renderHtml from './renderHtml';

function lastPathSegment(request) {
    const url = new URL(request.url);
    const pathSegments = url.pathname.split('/').filter(Boolean);
    return pathSegments[pathSegments.length - 1];
}

export default {
    async fetch(request, env) {
        const {DATABASE} = env;
        const client = lastPathSegment(request);
        const stmt = DATABASE.prepare('SELECT * FROM '.concat(client));
        const {results} = await stmt.all();

        return new Response(
            renderHtml(client, JSON.stringify(results, null, 2)),
            {
                headers: {
                    'content-type': 'text/html'
                },
            }
        )
    }
}
