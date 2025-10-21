/**
 * Gestion complète de l'authentification Admin
 * Marketplace Backend - Module Admin
 */

var AuthManager = function() {
    this.loginForm = document.getElementById('loginForm');
    this.emailInput = document.getElementById('email');
    this.passwordInput = document.getElementById('password');
    this.rememberMeCheckbox = document.getElementById('rememberMe');
    this.togglePasswordBtn = document.getElementById('togglePassword');
    this.btnLogin = document.getElementById('btnLogin');
    this.errorMessage = document.getElementById('errorMessage');
    this.errorText = document.getElementById('errorText');
    this.forgotPasswordLink = document.getElementById('forgotPasswordLink');
    
    this.init();
};

AuthManager.prototype.init = function() {
    this.checkSession();
    this.restoreRememberMe();
    this.attachEventListeners();
};

AuthManager.prototype.attachEventListeners = function() {
    var self = this;
    
    this.loginForm.addEventListener('submit', function(e) {
        self.handleLogin(e);
    });
    
    this.togglePasswordBtn.addEventListener('click', function() {
        self.togglePasswordVisibility();
    });
    
    this.forgotPasswordLink.addEventListener('click', function(e) {
        self.handleForgotPassword(e);
    });
    
    this.emailInput.addEventListener('input', function() {
        self.hideError();
    });
    
    this.passwordInput.addEventListener('input', function() {
        self.hideError();
    });
};

AuthManager.prototype.handleLogin = function(e) {
    var self = this;
    e.preventDefault();
    
    var email = this.emailInput.value.trim();
    var password = this.passwordInput.value;
    var rememberMe = this.rememberMeCheckbox.checked;
    
    console.log('=== DEBUT CONNEXION ===');
    console.log('Email:', email);
    console.log('Password length:', password.length);
    
    if (!this.validateEmail(email)) {
        this.showError('Veuillez entrer une adresse email valide');
        this.emailInput.focus();
        return;
    }
    
    if (!this.validatePassword(password)) {
        this.showError('Le mot de passe doit contenir au moins 6 caracteres');
        this.passwordInput.focus();
        return;
    }
    
    this.setLoading(true);
    this.hideError();
    
    console.log('Envoi de la requete de login...');
    this.sendLoginRequest(email, password).then(function(response) {
        console.log('Reponse recu:', response);
        
        if (response.success) {
            self.handleLoginSuccess(response, rememberMe);
        } else {
            var message = response.message || 'Erreur lors de la connexion';
            self.showError(message);
            self.setLoading(false);
        }
    }).catch(function(error) {
        console.error('Erreur catch:', error);
        var message = error.message || 'Erreur serveur. Veuillez reessayer.';
        self.showError(message);
        self.setLoading(false);
    });
};

AuthManager.prototype.sendLoginRequest = function(email, password) {
    var url = Config.API_BASE_URL + Config.API_ENDPOINTS.LOGIN;
    
    console.log('API_BASE_URL:', Config.API_BASE_URL);
    console.log('API_ENDPOINTS.LOGIN:', Config.API_ENDPOINTS.LOGIN);
    console.log('URL COMPLETE:', url);
    
    var body = {
        email: email,
        motDePasse: password
    };
    
    console.log('Body a envoyer:', JSON.stringify(body));
    console.log('Demarrage fetch...');
    
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    }).then(function(response) {
        console.log('Response recu');
        console.log('Status:', response.status);
        console.log('StatusText:', response.statusText);
        
        if (!response.ok) {
            console.error('Response not OK');
            return response.text().then(function(text) {
                console.error('Contenu erreur:', text);
                throw new Error('Erreur HTTP: ' + response.status);
            });
        }
        
        return response.json().then(function(data) {
            console.log('JSON recu:', data);
            return data;
        });
    }).catch(function(error) {
        console.error('Erreur dans fetch:', error.message);
        throw error;
    });
};

AuthManager.prototype.handleLoginSuccess = function(response, rememberMe) {
    console.log('Connexion reussie!');
    
    Storage.setUser(response.utilisateur);
    
    if (response.token) {
        Storage.setToken(response.token);
    }
    
    Storage.setRememberMe(rememberMe);
    
    Notify.success('Connexion reussie!');
    
    setTimeout(function() {
        window.location.href = 'dashboard.xhtml';
    }, 1500);
};

AuthManager.prototype.togglePasswordVisibility = function() {
    var isPassword = this.passwordInput.type === 'password';
    this.passwordInput.type = isPassword ? 'text' : 'password';
    
    var icon = this.togglePasswordBtn.querySelector('i');
    if (isPassword) {
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
};

AuthManager.prototype.handleForgotPassword = function(e) {
    e.preventDefault();
    Notify.info('Fonctionnalite en cours de developpement');
};

AuthManager.prototype.showError = function(message) {
    this.errorText.textContent = message;
    this.errorMessage.style.display = 'flex';
    
    this.loginForm.classList.add('shake');
    var self = this;
    setTimeout(function() {
        self.loginForm.classList.remove('shake');
    }, 500);
};

AuthManager.prototype.hideError = function() {
    this.errorMessage.style.display = 'none';
    this.loginForm.classList.remove('shake');
};

AuthManager.prototype.setLoading = function(loading) {
    this.btnLogin.disabled = loading;
    this.emailInput.disabled = loading;
    this.passwordInput.disabled = loading;
    this.rememberMeCheckbox.disabled = loading;
    
    var btnText = this.btnLogin.querySelector('.btn-text');
    var btnLoader = this.btnLogin.querySelector('.btn-loader');
    
    if (loading) {
        btnText.style.display = 'none';
        btnLoader.style.display = 'inline-block';
        this.btnLogin.classList.add('loading');
    } else {
        btnText.style.display = 'inline';
        btnLoader.style.display = 'none';
        this.btnLogin.classList.remove('loading');
    }
};

AuthManager.prototype.validateEmail = function(email) {
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
};

AuthManager.prototype.validatePassword = function(password) {
    return password && password.length >= 6;
};

AuthManager.prototype.restoreRememberMe = function() {
    if (Storage.getRememberMe()) {
        this.rememberMeCheckbox.checked = true;
        var user = Storage.getUser();
        if (user && user.email) {
            this.emailInput.value = user.email;
        }
    }
};

AuthManager.prototype.checkSession = function() {
    var user = Storage.getUser();
    var token = Storage.getToken();
    
    if (user && user.nomRole === 'ADMIN' && token) {
        window.location.href = 'dashboard.xhtml';
    }
};

document.addEventListener('DOMContentLoaded', function() {
    console.log('Page chargee, initialisation AuthManager');
    new AuthManager();
});

function logout() {
    Storage.clearSession();
    Notify.info('Vous avez ete deconnecte');
    
    setTimeout(function() {
        window.location.href = 'login.xhtml';
    }, 500);
}

function requireAuth() {
    var user = Storage.getUser();
    var token = Storage.getToken();
    
    if (!user || !token || user.nomRole !== 'ADMIN') {
        Notify.error('Acces non autorise. Veuillez vous connecter.');
        
        setTimeout(function() {
            window.location.href = 'login.xhtml';
        }, 1000);
        
        return false;
    }
    
    return true;
}

function getCurrentUser() {
    return Storage.getUser();
}

function isAdmin() {
    var user = Storage.getUser();
    return user && user.nomRole === 'ADMIN';
}

function getAuthToken() {
    return Storage.getToken();
}

function getAuthHeaders() {
    var token = Storage.getToken();
    return {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
    };
}
















