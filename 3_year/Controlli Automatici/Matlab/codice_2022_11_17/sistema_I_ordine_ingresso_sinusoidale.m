%
% Sistema del primo ordine: risposta ad ingresso sinusoidale
%
close all; clear all; clc;

% parametri del sistema
mu = 1;   % guadagno
T  = 0.1; % costante di tempo

% parametri della sinusoide in ingresso
omega = 1e2;     % pulsazione: 100 rad/s
tt = 0:1e-3:0.3; % intervallo temporale: da 0 ad 0.3 con passo 0.001

%% simulazione

fprintf('Pulsazione sinusoide in ingresso: %.2f rad/s (%.2f Hz)\n', omega, omega/(2*pi));
fprintf('Pulsazione di taglio a -3dB: %.2f rad/s (%.2f Hz)\n\n', 1/T, 1/(T*2*pi));

fprintf('Azione filtrante del sistema:\n');
fprintf('Guadagno: %.2f dB (cioè fattore moltiplicativo: %.2f)\n', -20*log10(sqrt(1+omega^2*T^2)), 1/sqrt(1+omega^2*T^2));
fprintf('Sfasamento: %.2f gradi\n', rad2deg(-atan(omega*T)));

% definizione del sistema
s = tf('s');
G = mu/(1+T*s);

% definizione dell'ingresso
uu = cos(omega*tt);

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
xlabel('t');
legend;
