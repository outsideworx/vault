function lastPathSegment(request) {
    const url = new URL(request.url);
    const pathSegments = url.pathname.split('/').filter(Boolean);
    return pathSegments[pathSegments.length - 1];
}

export default {
    async fetch(request, env) {
        const {DATABASE} = env;
        const pathSegment = lastPathSegment(request);
        const stmt = DATABASE.prepare('SELECT * FROM ${pathSegment}');
        const {results} = await stmt.all();

        const module = await import(`${pathSegment}.js`);
        return new Response(
            module.render(JSON.stringify(results, null, 2)),
            {
                headers: {
                    'content-type': 'text/html'
                },
            }
        )
    }
}
