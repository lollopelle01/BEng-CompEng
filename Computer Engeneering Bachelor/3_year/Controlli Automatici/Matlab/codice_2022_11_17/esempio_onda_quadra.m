%
% Azione filtrante dei sistemi dinamici: esempio onda quadra
%
close all; clear all; clc;

% Sistema: filtro PASSA-BASSO con pulsazione di taglio in a 10 rad/s
mu = 1;   % guadagno
T  = 0.05; % costante di tempo

% parametri dell'onda quadra in ingresso
omega = 2;      % pulsazione: 2 rad/s
tt = 0:1e-3:10; % intervallo temporale: da 0 ad 10 con passo 0.01

% parametri del rumore
enable_noise = false;
omega_noise = 1000;
amplitude_noise = 1;

%% simulazione

fprintf('Pulsazione segnale in ingresso: %.2f rad/s (%.2f Hz)\n', omega, omega/(2*pi));
fprintf('Pulsazione di taglio a -3dB: %.2f rad/s (%.2f Hz)\n\n', 1/T, 1/(T*2*pi));

% definizione del sistema
s = tf('s');
G = mu/(1+T*s);

% definizione dell'ingresso
uu = sign(sin(omega*tt));

if enable_noise
    uu = uu + amplitude_noise*sin(omega_noise*tt);
end

% simulazione sistema
yy = lsim(G, uu, tt);

%% plot

% diagramma di bode
figure;
bode(G);
box on; zoom on; grid on;

% plot ingresso-uscita
figure;
hold on; box on; zoom on; grid on;
plot(tt,uu,'LineWidth',1,'DisplayName','u(t)');
plot(tt,yy,'LineWidth',1,'DisplayName','y(t)');
ylim([-2.3,2.3]);
xlabel('t');
legend;
