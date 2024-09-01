clear all; close all; clc;

A_d = 20;
A_n = -20;

omega_d_max = 1;
omega_d_min = 1e-5;
omega_n_min = 1e3;
omega_n_max = 1e4;

figure(1);hold on;
Bnd_d_x = [omega_d_min; omega_d_max; omega_d_max; omega_d_min];
Bnd_d_y = [A_d; A_d; -150; -150];
patch(Bnd_d_x, Bnd_d_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);
Bnd_n_x = [omega_n_min; omega_n_max; omega_n_max; omega_n_min];
Bnd_n_y = [A_n; A_n; 150; 150];
patch(Bnd_n_x, Bnd_n_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);

mu = 20;
T1 = 0.1;
T2 = 0.001;
s = tf('s');
GG = mu/((1+T1*s)*(1+T2*s));

margin(GG)
grid on;
omega_c_min = 10;
omega_c_MAX = 50;
phi_spec = -45;
phi_low = -270;
Bnd_Mf_x = [omega_c_min; omega_c_MAX; omega_c_MAX; omega_c_min];
Bnd_Mf_y = [phi_spec; phi_spec; phi_low; phi_low];
patch(Bnd_Mf_x, Bnd_Mf_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);