%
% Sistema del secondo ordine: risposta ad ingresso sinusoidale
%
close all; clear all; clc;

% parametri del sistema
mu = 1;   % guadagno
xi = 0.2; % smorzamento
omegan = 130; % pulsazione naturale: 130 rad/s

% parametri della sinusoide in ingresso
omega = 1e2;     % pulsazione: 100 rad/s
tt = 0:1e-3:0.5; % intervallo temporale: da 0 ad 0.5 con passo 0.001

%% simulazione

fprintf('Pulsazione sinusoide in ingresso: %.2f rad/s (%.2f Hz)\n', omega, omega/(2*pi));
fprintf('Pulsazione di taglio: %.2f rad/s (%.2f Hz)\n\n', omegan, omegan/(2*pi));
fprintf('Picco di risonanza: %.2f dB in corrispondenza della pulsazione %.2f rad/s (%.2f Hz)\n\n', -20*log10(2*abs(xi)*sqrt(1-xi^2)), omegan*sqrt(1-2*xi^2), omegan*sqrt(1-2*xi^2)/(2*pi));

fprintf('Azione filtrante del sistema:\n');
fprintf('Guadagno: %.2f dB (cioè fattore moltiplicativo: %.2f)\n', -20*log10(sqrt((1-omega^2/omegan^2)^2 + 4*xi^2*omega^2/omegan^2)), 1/sqrt((1-omega^2/omegan^2)^2 + 4*xi^2*omega^2/omegan^2));
fprintf('Sfasamento: %.2f gradi\n', rad2deg(-atan2(2*xi*omega/omegan, 1-omega^2/omegan^2)));

% definizione del sistema
s = tf('s');
G = mu*omegan^2/(s^2 + 2*xi*omegan*s + omegan^2);

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
