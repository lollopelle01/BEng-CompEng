clear all; close all; clc;
%% sistema

mu = 5000;
T1 = 0.0005;
T2 = 0.001;
s = tf('s');
GG = mu/((1+T1*s)*(1+T2*s));

%% specifiche 

A_d = 20;
A_n = -20;

omega_d_max = 10;
omega_d_min = 1e-5;
omega_n_min = 2e3;
omega_n_max = 1e5;
T_star = 0.05;
S_star = 0.35;

%% mappatura
logsq = (log(S_star))^2;
xi_star = sqrt(logsq/(pi^2+logsq));
Mf = xi_star*100;

omega_c_min = 4.6/(T_star*xi_star);
omega_c_MAX = omega_n_min;

figure(1);hold on;
Bnd_d_x = [omega_d_min; omega_d_max; omega_d_max; omega_d_min];
Bnd_d_y = [A_d; A_d; -150; -150];
patch(Bnd_d_x, Bnd_d_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);
Bnd_d_x = [omega_c_min; omega_d_min; omega_d_min; omega_c_min];
Bnd_d_y = [0; 0; -150; -150];
patch(Bnd_d_x, Bnd_d_y,'b','FaceAlpha',0.2,'EdgeAlpha',0);
Bnd_n_x = [omega_n_min; omega_n_max; omega_n_max; omega_n_min];
Bnd_n_y = [A_n; A_n; 150; 150];
patch(Bnd_n_x, Bnd_n_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);


Rs = 1/s;
Gext = Rs*GG;
margin(Gext)
grid on;



phi_spec = -180+Mf;
phi_low = -270;
Bnd_Mf_x = [omega_c_min; omega_c_MAX; omega_c_MAX; omega_c_min];
Bnd_Mf_y = [phi_spec; phi_spec; phi_low; phi_low];
patch(Bnd_Mf_x, Bnd_Mf_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);

if 0
    return;
end
%% rete ritardatrice
omega_c_star = 400;
Mf_star = Mf+1;
[mag_omega_c_star, arg_omega_c_star, omega_c_star] = bode(Gext, omega_c_star);
mag_omega_c_star_dB = 20*log10(mag_omega_c_star);
phi_star = -180+Mf_star-arg_omega_c_star;
M_star = 10^(-mag_omega_c_star_dB/20);

alpha_tau_b = (M_star - cos(phi_star*pi/180))/omega_c_star/sin(phi_star*pi/180);
tau_b = (cos(phi_star*pi/180) - inv(M_star))/omega_c_star/sin(phi_star*pi/180);
alpha_b = alpha_tau_b / tau_b;

check_flag = min(alpha_tau_b, tau_b);
if check_flag < 0
    disp('Errore: polo/zero positivo');
    return;
end

Rd = (1 + alpha_b * tau_b*s)/(1 + tau_b*s);
L = Gext*Rd;

figure(2);hold on;
Bnd_d_x = [omega_d_min; omega_d_max; omega_d_max; omega_d_min];
Bnd_d_y = [A_d; A_d; -150; -150];
patch(Bnd_d_x, Bnd_d_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);
Bnd_d_x = [omega_c_min; omega_d_min; omega_d_min; omega_c_min];
Bnd_d_y = [0; 0; -150; -150];
patch(Bnd_d_x, Bnd_d_y,'b','FaceAlpha',0.2,'EdgeAlpha',0);
Bnd_n_x = [omega_n_min; omega_n_max; omega_n_max; omega_n_min];
Bnd_n_y = [A_n; A_n; 150; 150];
patch(Bnd_n_x, Bnd_n_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);

margin(L)
grid on;

phi_spec = -180+Mf;
phi_low = -270;
Bnd_Mf_x = [omega_c_min; omega_c_MAX; omega_c_MAX; omega_c_min];
Bnd_Mf_y = [phi_spec; phi_spec; phi_low; phi_low];
patch(Bnd_Mf_x, Bnd_Mf_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);