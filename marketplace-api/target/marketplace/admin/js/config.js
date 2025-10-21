/**
 * Configuration globale de l'application Admin
 * Marketplace Backend - Configuration centralisée
 */

var Config = {
    API_BASE_URL: 'http://localhost:8080/marketplace/api',
    
    API_ENDPOINTS: {
        LOGIN: '/admin/auth/login',
        LOGOUT: '/admin/auth/logout',
        STATUS: '/admin/auth/status',
        CREATE_USER: '/admin/auth/create-user',
        DASHBOARD: '/admin/dashboard',
        UTILISATEURS: '/admin/utilisateurs',
        BOUTIQUES: '/admin/boutiques',
        PRODUITS: '/admin/produits',
        COMMANDES: '/admin/commandes',
        PAIEMENTS: '/admin/paiements',
        PARAMETRES: '/admin/parametres',
        STATISTIQUES: '/admin/dashboard/statistiques'
    },
    
    STORAGE_KEYS: {
        USER: 'marketplace_admin_user',
        TOKEN: 'marketplace_admin_token',
        REMEMBER_ME: 'marketplace_admin_remember'
    },
    
    SESSION_TIMEOUT: 30 * 60 * 1000,
    
    MESSAGES: {
        LOGIN_SUCCESS: 'Connexion reussie ! Redirection...',
        LOGIN_ERROR: 'Email ou mot de passe incorrect',
        SERVER_ERROR: 'Erreur serveur. Veuillez reessayer.',
        NETWORK_ERROR: 'Erreur de connexion. Verifiez votre connexion internet.',
        SESSION_EXPIRED: 'Votre session a expire. Veuillez vous reconnecter.',
        UNAUTHORIZED: 'Acces non autorise'
    }
};

/**
 * Utilitaire pour les requetes HTTP
 */
var HTTP = {
    get: function(endpoint, options) {
        if (!options) {
            options = {};
        }
        return this.request('GET', endpoint, null, options);
    },
    
    post: function(endpoint, data, options) {
        if (!options) {
            options = {};
        }
        return this.request('POST', endpoint, data, options);
    },
    
    put: function(endpoint, data, options) {
        if (!options) {
            options = {};
        }
        return this.request('PUT', endpoint, data, options);
    },
    
    delete: function(endpoint, options) {
        if (!options) {
            options = {};
        }
        return this.request('DELETE', endpoint, null, options);
    },
    
    request: function(method, endpoint, data, options) {
        if (!data) {
            data = null;
        }
        if (!options) {
            options = {};
        }
        
        var url = Config.API_BASE_URL + endpoint;
        
        var headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };
        
        if (options.headers) {
            for (var key in options.headers) {
                headers[key] = options.headers[key];
            }
        }
        
        var token = Storage.getToken();
        if (token) {
            headers['Authorization'] = 'Bearer ' + token;
        }
        
        var config = {
            method: method,
            headers: headers
        };
        
        for (var key in options) {
            if (key !== 'headers') {
                config[key] = options[key];
            }
        }
        
        if (data && (method === 'POST' || method === 'PUT')) {
            config.body = JSON.stringify(data);
        }
        
        return fetch(url, config).then(function(response) {
            if (!response.ok) {
                if (response.status === 401) {
                    Storage.clearSession();
                    window.location.href = 'login.xhtml';
                    throw new Error(Config.MESSAGES.UNAUTHORIZED);
                }
                
                return response.json().then(function(errorData) {
                    var errorMessage = errorData && errorData.message ? errorData.message : 'Erreur HTTP ' + response.status;
                    throw new Error(errorMessage);
                }).catch(function(e) {
                    throw new Error('Erreur HTTP ' + response.status);
                });
            }
            
            return response.json();
            
        }).catch(function(error) {
            console.error('Erreur requete HTTP:', error);
            
            if (error.message.indexOf('Failed to fetch') !== -1) {
                throw new Error(Config.MESSAGES.NETWORK_ERROR);
            }
            
            throw error;
        });
    }
};

/**
 * Utilitaire pour le stockage local
 */
var Storage = {
    setUser: function(user) {
        localStorage.setItem(Config.STORAGE_KEYS.USER, JSON.stringify(user));
    },
    
    getUser: function() {
        var user = localStorage.getItem(Config.STORAGE_KEYS.USER);
        return user ? JSON.parse(user) : null;
    },
    
    setToken: function(token) {
        localStorage.setItem(Config.STORAGE_KEYS.TOKEN, token);
    },
    
    getToken: function() {
        return localStorage.getItem(Config.STORAGE_KEYS.TOKEN);
    },
    
    setRememberMe: function(remember) {
        localStorage.setItem(Config.STORAGE_KEYS.REMEMBER_ME, remember.toString());
    },
    
    getRememberMe: function() {
        return localStorage.getItem(Config.STORAGE_KEYS.REMEMBER_ME) === 'true';
    },
    
    clearSession: function() {
        localStorage.removeItem(Config.STORAGE_KEYS.USER);
        localStorage.removeItem(Config.STORAGE_KEYS.TOKEN);
    },
    
    clearAll: function() {
        localStorage.clear();
    }
};

/**
 * Utilitaire pour les notifications
 */
var Notify = {
    success: function(message, duration) {
        if (!duration) {
            duration = 3000;
        }
        this.show(message, 'success', duration);
    },
    
    error: function(message, duration) {
        if (!duration) {
            duration = 5000;
        }
        this.show(message, 'error', duration);
    },
    
    info: function(message, duration) {
        if (!duration) {
            duration = 3000;
        }
        this.show(message, 'info', duration);
    },
    
    show: function(message, type, duration) {
        if (!type) {
            type = 'info';
        }
        if (!duration) {
            duration = 3000;
        }
        
        var notification = document.createElement('div');
        notification.className = 'notification notification-' + type;
        
        var icon = this.getIcon(type);
        notification.innerHTML = '<i class="fas fa-' + icon + '"></i><span>' + message + '</span>';
        
        document.body.appendChild(notification);
        
        var self = this;
        setTimeout(function() {
            notification.classList.add('show');
        }, 10);
        
        setTimeout(function() {
            notification.classList.remove('show');
            setTimeout(function() {
                if (document.body.contains(notification)) {
                    document.body.removeChild(notification);
                }
            }, 300);
        }, duration);
    },
    
    getIcon: function(type) {
        var icons = {
            'success': 'check-circle',
            'error': 'exclamation-circle',
            'info': 'info-circle',
            'warning': 'exclamation-triangle'
        };
        return icons[type] || 'info-circle';
    }
};

/**
 * Utilitaire pour la validation
 */
var Validator = {
    isValidEmail: function(email) {
        var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    },
    
    isValidPassword: function(password) {
        return password && password.length >= 6;
    },
    
    isValidPhone: function(phone) {
        var regex = /^(77|78|76|70|75)[0-9]{7}$/;
        var cleanPhone = phone.replace(/\s/g, '');
        return regex.test(cleanPhone);
    }
};

/**
 * Utilitaire pour le formatage
 */
var Formatter = {
    number: function(value) {
        return new Intl.NumberFormat('fr-FR').format(value);
    },
    
    currency: function(value) {
        return new Intl.NumberFormat('fr-FR', {
            style: 'currency',
            currency: 'XOF',
            minimumFractionDigits: 0
        }).format(value);
    },
    
    date: function(date, format) {
        if (!format) {
            format = 'short';
        }
        
        var d = new Date(date);
        
        if (format === 'short') {
            return d.toLocaleDateString('fr-FR');
        } else if (format === 'long') {
            return d.toLocaleDateString('fr-FR', {
                weekday: 'long',
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });
        } else if (format === 'datetime') {
            return d.toLocaleString('fr-FR');
        }
        
        return d.toLocaleDateString('fr-FR');
    },
    
    dateRelative: function(date) {
        var now = new Date();
        var d = new Date(date);
        var diff = now - d;
        
        var seconds = Math.floor(diff / 1000);
        var minutes = Math.floor(seconds / 60);
        var hours = Math.floor(minutes / 60);
        var days = Math.floor(hours / 24);
        
        if (seconds < 60) {
            return 'A l\'instant';
        } else if (minutes < 60) {
            var label = minutes > 1 ? 's' : '';
            return 'Il y a ' + minutes + ' minute' + label;
        } else if (hours < 24) {
            var label = hours > 1 ? 's' : '';
            return 'Il y a ' + hours + ' heure' + label;
        } else if (days < 7) {
            var label = days > 1 ? 's' : '';
            return 'Il y a ' + days + ' jour' + label;
        } else {
            return this.date(date);
        }
    }
};


















