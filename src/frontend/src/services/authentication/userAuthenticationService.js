import {ref} from 'vue';

export const getCookie = function(cookieName) {
    let cookie = {};
    document.cookie.split(';').forEach(function(el) {
        let [key,value] = el.split('=');
        cookie[key.trim()] = value;
    })
    return cookie[cookieName];
}

export const deleteCookie= function( name, path, domain ) {
    if( getCookie( name ) ) {
      document.cookie = name + "=" +
        ((path) ? ";path="+path:"")+
        ((domain)?";domain="+domain:"") +
        ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
    }
}

export const setCookie = function(name, value) {
    document.cookie = name + "=" + value + "; session; path=/";
}

export const isUserAuthenticated = ref(false);

export const userRole = ref('');