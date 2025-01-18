import { html } from './renderHtml';

export default {
    async fetch(request, env) {
        const { DATABASE } = env;
        return new Response(html, {
            headers: {
                'content-type': 'text/html; charset=UTF-8'
            }
        });
    },
};
