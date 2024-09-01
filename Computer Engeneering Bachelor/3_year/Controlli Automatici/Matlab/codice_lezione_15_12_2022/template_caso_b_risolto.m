% Esempio progetto regolatore per sistema carello
% 
% Controlli Automatici T
% Giuseppe Notarstefano
% Andrea Testa
% 12/2022
%
% SPECIFICHE
% 
% -) Errore a regime in risposta a un gradino w(t) = 2*1(t) e d(t) = 2*1(t) pari a 0.1
%
% -) Attenuazione di almeno 15dB per d(t)
%       con [omega_d_min, omega_d_MAX] = [0,0.1]
%
% -) Attenuazione di almeno 35dB per n(t)
%       con [omega_n_min, omega_n_MAX] = [5*10^4,5*10^5]
%
% -) S% <= 5%
% -) Ta,1 <= 0.08 s
%

clear all; close all; clc


%%Scrivere specifiche

% ampiezze gradini
WW = 2;
DD = 2;

% errore a regime
e_star = 0.1;

% attenuazione disturbo sull'uscita
A_d = 15;
omega_d_MAX = 0.1;

% attenuazione disturbo di misura
A_n = 35;
omega_n_min = 5e4;
omega_n_MAX = 5e5;

% Sovraelongazione massima e tempo d'assestamento all'1%
S_100_spec = 0.05;
T_a1_spec = 0.08;

%% Creazione sistema

% Parametri

mm = 0.5; % [kg] Massa
kk = 80;  % [N/m^2] Coefficiente di attrito dinamico
bb = 10;  % [N/m] Coefficiente di rigidezza della molla
% parametri sistema del secondo ordine
mu_sys = 1/kk;
omegan_sys = sqrt(kk/mm);
xi_sys = bb/(2*sqrt(kk*mm));

% scrivere funzione di trasferimento
s = tf('s');
GG = mu_sys * omegan_sys^2/(s^2 + 2*xi_sys*omegan_sys*s + omegan_sys^2);

%% Diagramma di Bode

figure(1);
bode(GG);
grid on, zoom on;

if 0
    return
end

%% Regolatore statico - proporzionale senza poli nell'origine

%scrivere valore minimo prescritto per L(0)
mu_s = (DD+WW)/e_star;

%scrivere guadagno minimo del regolatore ottenuto come L(0)/G(0)
G_0 = abs(evalfr(GG,0));
RR_s = mu_s / G_0; 

% Sistema esteso
GG_e = RR_s*GG;



%% Diagrammi di Bode di Ge con specifiche

figure(2);
hold on;

%scrivere calcolo specifiche S% => Margine di fase
logsq = (log(S_100_spec))^2;
xi = sqrt(logsq/(pi^2+logsq));
Mf_spec = xi*100
omega_Ta_MAX = 460/(Mf_spec*T_a1_spec); 

% Specifiche su d
omega_d_min = 0.0001; % lower bound per il plot
Bnd_d_x = [omega_d_min; omega_d_MAX; omega_d_MAX; omega_d_min];
Bnd_d_y = [A_d; A_d; -150; -150];
patch(Bnd_d_x, Bnd_d_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);

% Specifiche su n
Bnd_n_x = [omega_n_min; omega_n_MAX; omega_n_MAX; omega_n_min];
Bnd_n_y = [-A_n; -A_n; 100; 100];
patch(Bnd_n_x, Bnd_n_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);

% Specifiche tempo d'assestamento (minima pulsazione di taglio)
omega_Ta_min = 1e-4; % lower bound per il plot
Bnd_Ta_x = [omega_Ta_min; omega_Ta_MAX; omega_Ta_MAX; omega_Ta_min];
Bnd_Ta_y = [0; 0; -150; -150];
patch(Bnd_Ta_x, Bnd_Ta_y,'b','FaceAlpha',0.2,'EdgeAlpha',0);

% Legenda colori
Legend_mag = ["A_d", "A_n", "\omega_{c,min}", "G(j\omega)"];
legend(Legend_mag);

% Plot Bode con margini di stabilità
margin(GG_e);
grid on; zoom on;



% Specifiche sovraelongazione (margine di fase)
omega_c_min = omega_Ta_MAX;
omega_c_MAX = omega_n_min;

phi_spec = Mf_spec - 180;
phi_low = -270; % lower bound per il plot

Bnd_Mf_x = [omega_c_min; omega_c_MAX; omega_c_MAX; omega_c_min];
Bnd_Mf_y = [phi_spec; phi_spec; phi_low; phi_low];
patch(Bnd_Mf_x, Bnd_Mf_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);
hold on;

% Legenda colori
Legend_arg = ["G(j\omega)"; "M_f"];
legend(Legend_arg);

% STOP qui per regolatore statico
if 0
    return;
end

%% Design del regolatore dinamico

Mf_star = Mf_spec+5; 
omega_c_star = 200;

% scrivere formule di inversione
[mag_omega_c_star, arg_omega_c_star, ~] = bode(GG_e, omega_c_star);
mag_omega_c_star_db = 20*log10(mag_omega_c_star);

M_star = 10^(-mag_omega_c_star_db/20);
phi_star = Mf_star - 180 - arg_omega_c_star;

alpha_tau = (cos(phi_star*pi/180) - 1/M_star)/(omega_c_star*sin(phi_star*pi/180));
tau = (M_star - cos(phi_star*pi/180))/(omega_c_star*sin(phi_star*pi/180));
alpha = alpha_tau / tau;


check_flag = min(tau, alpha_tau);
if check_flag < 0
    disp('Errore: polo/zero positivo');
    return;
end


%% Diagrammi di Bode con specifiche includendo regolatore dinamico

R_d = (1 + tau*s)/(1 + alpha_tau*s); % rete anticipatrice
LL = R_d*GG_e; % funzione di anello

figure(3);
hold on;


% Specifiche su ampiezza
patch(Bnd_d_x, Bnd_d_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);
patch(Bnd_n_x, Bnd_n_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);
patch(Bnd_Ta_x, Bnd_Ta_y,'b','FaceAlpha',0.2,'EdgeAlpha',0);
legend(Legend_mag);

% Plot Bode con margini di stabilità
margin(LL);
grid on; zoom on;

% Specifiche su fase
patch(Bnd_Mf_x, Bnd_Mf_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);
hold on;
legend(Legend_arg);




% STOP qui per sistema con controllore dinamico + specifiche
if 0
    return;
end


%% Check prestazioni in anello chiuso

% Funzione di sensitività complementare
FF = LL/(1+LL);

% Risposta al gradino
figure(5);

T_simulation = 0.5;
[y_step,t_step] = step(WW*FF, T_simulation);
plot(t_step,y_step,'b');
grid on, zoom on, hold on;

% vincolo sovraelongazione
patch([0,T_simulation,T_simulation,0],[WW*(1+S_100_spec),WW*(1+S_100_spec),WW+1,WW+1],'r','FaceAlpha',0.3,'EdgeAlpha',0.5);
ylim([0,WW+1]);

% vincolo tempo di assestamento all'1%
LV = abs(evalfr(WW*FF,0)); % valore limite gradino: W*F(0)
patch([T_a1_spec,T_simulation,T_simulation,T_a1_spec],[LV*(1-0.01),LV*(1-0.01),0,0],'g','FaceAlpha',0.1,'EdgeAlpha',0.5);
patch([T_a1_spec,T_simulation,T_simulation,T_a1_spec],[LV*(1+0.01),LV*(1+0.01),LV+1,LV+1],'g','FaceAlpha',0.1,'EdgeAlpha',0.1);

Legend_step = ["Risposta al gradino"; "Vincolo sovraelongazione"; "Vincolo tempo di assestamento"];
legend(Legend_step);

% Diagramma di bode sistema in anello chiuso
figure(6);
bode(FF)
grid on, zoom on

if 0
    return;
end


%% Check disturbo in uscita

% Funzione di sensitività
SS = 1/(1+LL);
figure(7);

% Simulazione disturbo a pulsazione 0.05
omega_d = 0.05;
tt = (0:1e-2:1e3)';
dd = DD*cos(omega_d*tt);
y_d = lsim(SS,dd,tt);
hold on, grid on, zoom on
plot(tt,dd,'m')
plot(tt,y_d,'b')
grid on
legend('dd','y_d')
