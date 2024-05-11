% Esempi margini ampiezza e fase
% 
% Controlli Automatici T
% 2022/23
%
% NOTA: ogni blocco di codice è circondato da "if 0/1 ... end". I blocchi
% con 0 solo disabilitati, quelli con 1 sono abilitati. Mettere ad
% 1 solo UN blocco per volta
%

clear all, close all, clc




s = tf('s');


%% ESEMPIO M_a e M_f ben definiti

if 1
mu = 1e2;
TT1 = 1;
TT2 = 1e-2;
TT3 = 1e-3;

LL = mu/(1 + TT1*s)/(1 + TT2*s)/(1 + TT3*s)

[M_a,M_f,omega_pi,omega_c] = margin(LL)
M_a_db = 20*log10(M_a)
end



%% ESEMPI CASI PATOLOGICI

%% Intersezioni multiple
if 0
mu = 10;
xi = 0.01;
omega_n = 1e2;

LL = mu/s/(1 + 2*xi/omega_n*s + s^2/omega_n^2)

[M_a,M_f,omega_pi,omega_c] = margin(LL)
M_a_db = 20*log10(M_a)  

end



%% No intersezioni
if 0
mu = 0.1;
tau = 0.1;
TT = 1;

LL = mu*(1 + tau*s)/(1 + TT*s)

[M_a,M_f,omega_pi,omega_c] = margin(LL)
M_a_db = 20*log10(M_a)  
end



%% ROBUSTEZZA 

%% Incertezze guadagno

if 0
    
% Scegliere uno dei valori di mu
mu = 0.1;
% mu = 0.1*15;
% mu = 0.1*110;
TT = 1;
TT2 = 0.1;

LL = mu/s/(1 + TT*s)/(1 + TT2*s)

[M_a,M_f,omega_pi,omega_c] = margin(LL)
M_a_db = 20*log10(M_a)  
end

%% Ritardo in ingresso
if 0
mu = 0.1;
TT = 1;
TT2 = 0.1;

% Scegliere uno dei valori di tau
tau = 0;
% tau = 7;
% tau = 14;

LL = exp(-tau*s)*mu/s/(1 + TT*s)/(1 + TT2*s)

[M_a,M_f,omega_pi,omega_c] = margin(LL)
M_a_db = 20*log10(M_a)  
end



figure(1)
margin(LL)
grid on, zoom on

